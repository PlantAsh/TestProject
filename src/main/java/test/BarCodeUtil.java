package test;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 获取条形码
 *
 * @author swu
 * @Date 2019-9-17
 */
public class BarCodeUtil {

    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    private static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public static void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        // 如果想要其他类型的条码(CODE 39, EAN-8...)直接获取相关对象Code39Bean...等等
        Code128Bean bean = new Code128Bean();

        // 分辨率：值越大条码越长，分辨率越高。
        final int dpi = 80;
        // UnitConv 是barcode4j 提供的单位转换的实体类，用于毫米mm,像素px,英寸in,点pt之间的转换
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);

        // 设置条码每一条的宽度
        bean.setModuleWidth(moduleWidth);
        // 设置高度
        bean.setBarHeight(10.0D);
        // 设置两侧是否加空白
        bean.doQuietZone(true);

        String format = "image/png";
        try {

            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生成条形码
            bean.generateBarcode(canvas, msg);

            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *@参数
     *@返回值
     *@创建人  cx
     *@创建时间
     *@描述   条形码的 64 位字符串
     */
    public static String getBarCodeBase64Str(String orderNo)  {

        byte[] bytes = Base64.encodeBase64(generate(orderNo));
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
