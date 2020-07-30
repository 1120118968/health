package com.example.mobilehomework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/*
*
* 生成随机验证码
* */
public class IdentifyCode {
    private static final char[] CHARS={
            '0','1','2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    //单例模式
    private static IdentifyCode identifyCode;

    public static IdentifyCode getInstance(){
        if(identifyCode == null){
            identifyCode = new IdentifyCode();

        }
        return identifyCode;
    }
//定义验证码的个数
    private static final int CODE_LENGTH = 4 ;
    //线条数目
    private static final int LINE_NUMBER = 5;
    //定义字体大小
    private static  final int FONT_SIZE = 50;
    //初始化padding变化范围
    private static final int BASE_PADDING_LEFT = 10,RANGE_PADDING_LEFT=100,BASE_PADDING_TOP=75,
            RANGE_PADDING_TOP = 50;
    //验证码默认宽高
    private static final int DEFAULT_WIDTH = 400,DEFAULT_HEIGHT = 150;
    //画布的宽高
    private int width = DEFAULT_WIDTH,height = DEFAULT_HEIGHT;
    //字体的随机位置
    private int base_padding_left=BASE_PADDING_LEFT,range_padding_left=RANGE_PADDING_LEFT,
            base_padding_top=BASE_PADDING_TOP,range_padding_top=RANGE_PADDING_TOP;
    //验证码个数。线条数，字体大小
    private int codeLengt = CODE_LENGTH,lineNUmber = LINE_NUMBER,fonSize = FONT_SIZE;

    private  String code;
    private int padding_left,padding_top;
    private Random random = new Random();
    public String getCode() {

        return code;

    }
    //验证码图片的生成
    public Bitmap createBitmap(){
        padding_left = 0;
        padding_top = 0;
        //创建指定格式，大小的位图 //config。ARGB_8888是一种色彩的存储方法
        Bitmap bp = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bp);

        code = createCode();
        //画布填充色为白色
        c.drawColor(Color.WHITE);
        //创建画笔
        Paint p = new Paint();
        //设置画笔抗锯齿
        p.setAntiAlias(true);
        p.setTextSize(fonSize);
        //在画布上画验证码
       // p.setFontMetrics  fontMetrics = p.getFontMetrics();
        for (int i=0;i<code.length();i++){
            randomTextStyle(p);
            randomPadding();
            //padding_left，padding_top的文字基线
            c.drawText(code.charAt(i)+"",padding_left,padding_top,p);

        }
        //画干扰线
        for (int i = 0; i<lineNUmber;i++){
            drawLine(c,p);
        }
        //保存画布
        c.save();
        c.restore();
        return bp;
    }
    //画线
    private void drawLine(Canvas c, Paint p) {

        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        p.setStrokeWidth(1);
        p.setColor(color);
        c.drawLine(startX,startY,stopX,stopY,p);
    }
    //生成随机颜色  利用RGB
    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256)/rate;
        int green = random.nextInt(256)/rate;
        int blue = random.nextInt(256)/rate;
        return Color.rgb(red,green,blue);
    }

    private void randomPadding() {

        padding_left += base_padding_left +random.nextInt(range_padding_left);
        padding_top = base_padding_top+random.nextInt(range_padding_top);
        //不使用+=以此防止出界
        /*
        * 此作为基线的padding_left，是从base_padding_left依次增加的。而每次增加的幅度，由random随机产生。最大幅度由range_padding_left控制。

而padding_top,因为是可以上上下下，但是，注意不能出界。所以不是padding_top+=base_padding_top+幅度，而是=；也就是说，从左到右，不是依次往下降的，而是上蹿下跳的。这样只要控制base_padding_top和range_padding_top就不会出界了。

        * */
    }
    //随机文字样式，颜色，文字粗细，倾斜度

    private void randomTextStyle(Paint p) {
        int color = randomColor();
        p.setColor(color);
        p.setFakeBoldText(random.nextBoolean());
        //true 黑体 false 非黑体
        double skew = random.nextInt(11)/10;
        //随机true或者false来生成正数或者负数  以表是文字的倾斜度 负数右倾斜
        skew = random.nextBoolean()? skew:-skew;
        p.setUnderlineText(true);//下划线
        p.setStrikeThruText(true);//删除线
    }
     //生成验证码
    private String createCode() {
        StringBuilder sb = new StringBuilder();
        //利用random生成随机下标
        for (int i = 0;i<codeLengt;i++){
            sb.append(CHARS[random.nextInt(CHARS.length)]);

        }
        return sb.toString();
    }
}
