package com.fr.canvas;

import com.fr.canvas.log.FineLoggerFactory;
import com.fr.canvas.util.StringUtils;
import com.google.common.io.BaseEncoding;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.image.BufferedImage;

/**
 * 辅助处理canvas的图片绘制接口(drawImage)，处理canvas和image。
 */
public class ImageUtils {

    public static final Map<String, CanvasAdapter> CANVAS_HOLDER = new ConcurrentHashMap<String, CanvasAdapter>();


    public static BufferedImage get(String id) {
        CanvasAdapter canvasAdapter = CANVAS_HOLDER.get(id);
        return canvasAdapter == null ? null : canvasAdapter.getCanvas();
    }

    public static void put(String id, CanvasAdapter canvas) {
        CANVAS_HOLDER.put(id, canvas);
    }

    public static void remove(String id) {
        CANVAS_HOLDER.remove(id);
    }


    public static BufferedImage getOrCreate(String src) {
        BufferedImage image = create(src);
        return image;
    }

    public static BufferedImage create(String src) {
        try{
            return StringUtils.isEmpty(src) ? null : src.startsWith("data:") ? createByBase64(src) : createByUrl(src);
        } catch (IOException ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
            return null;
        }
    }

    private static BufferedImage createByBase64(String base64) throws IOException {
        String imageData = base64.split("base64,")[1];
        byte[] data = BaseEncoding.base64().decode(imageData);
        return ImageIO.read(new ByteArrayInputStream(data));
    }

    private static BufferedImage createByUrl(String strUrl) throws IOException {
        URL url = new URL(strUrl); //声明url对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //打开连接
        connection.setDoOutput(true);
        InputStream stream = connection.getInputStream();
        connection.disconnect();
        return ImageIO.read(stream);
    }
}
