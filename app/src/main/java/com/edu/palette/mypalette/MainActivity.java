package com.edu.palette.mypalette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        setToolbarColor(bit);
    }

    /*
    如果你需要不断地生成图像或对象的排序列表调色板，考虑缓存 的Palette情况下，防止慢UI性能。您也不应该在主线程上创建调色板。
     */

    // 同步生成调色板并返回
    public Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }

    // 异步生成调色板并使用不同的调色板
// thread 使用 onGenerated()
    public void createPaletteAsync(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                // 使用生成实例

            }
        });
    }



    /*
    将Palette.Builder允许您通过选择多种颜色怎么会出现在结果调色板，什么区域图像的生成器用来生成调色板，
    什么颜色都允许在调色板中自定义您的调色板。例如，您可以过滤掉黑色或确保构建器仅使用图像的上半部分来生成调色板。

使用以下方法从Palette.Builder类中微调调色板的大小和颜色：

    addFilter()
此方法添加一个过滤器，指示生成的调色板中允许的颜色。传递您自己 的方法并修改其方法以确定从调色板中过滤哪些颜色。 Palette.FilterisAllowed()
maximumColorCount()
此方法设置调色板中的最大颜色数。默认值为16，最佳值取决于源图像。对于风景，最佳值范围为8-16，而带有人脸的图片通常具有介于24-32之间的值。将 Palette.Builder需要更长的时间来产生更多的颜色的调色板。
setRegion()
此方法指示构建器在创建调色板时使用的位图区域。您只能在从位图生成调色板时使用此方法，并且它不会影响原始图像。
addTarget()
此方法允许您通过向Target 构建器添加颜色配置文件来执行自己的颜色匹配 。如果默认值Target不够，高级开发人员可以Target使用a 创建自己的s Target.Builder。
     */


    // 设置给定的工具栏的背景和文本颜色
//位图图像匹配
    public void setToolbarColor(Bitmap bitmap) {
        // 生成调色板并获得充满活力的样本
//参阅createPaletteAsync方法
//从上面的代码片段
        /*
        //                Palette.Swatch swatch = palette.getVibrantSwatch();  //充满活力的色调
//                Palette.Swatch swatch = palette.getLightVibrantSwatch();  //充满活力的亮色调
//                Palette.Swatch swatch = palette.getDarkVibrantSwatch();  //充满活力的暗色调

//                Palette.Swatch swatch = palette.getMutedSwatch();  //柔和的色调
//                Palette.Swatch swatch = palette.getLightMutedSwatch();  //柔和的亮色调
//                Palette.Swatch swatch = palette.getDarkMutedSwatch();  //柔和的暗色调
         */
        Palette p = createPaletteSync(bitmap);
        Palette.Swatch vibrantSwatch = p.getVibrantSwatch();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // 加载默认颜色
        int backgroundColor = ContextCompat.getColor(this,
                R.color.colorPrimaryDark);
        int textColor = ContextCompat.getColor(this,
                R.color.colorAccent);

        /*
        swatch.getPopulation():  //像素的数量
swatch.getRgb():  //RGB颜色
swatch.getHsl():  //HSL颜色
swatch.getBodyTextColor():  //用于内容正文文本的颜色
swatch.getTitleTextColor():  //标题文本的颜色
         */
        // 检查有活力的样本是否可用
        if (vibrantSwatch != null) {
            backgroundColor = vibrantSwatch.getRgb();
            textColor = vibrantSwatch.getTitleTextColor();
        }

        // 设置工具栏背景和文本颜色
        toolbar.setBackgroundColor(backgroundColor);
        toolbar.setTitleTextColor(textColor);
    }


}
