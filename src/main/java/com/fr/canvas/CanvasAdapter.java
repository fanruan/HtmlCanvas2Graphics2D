package com.fr.canvas;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class CanvasAdapter {

    private BufferedImage canvas;

    private ContextAdapter contextAdapter;

    public CanvasAdapter() {
        this(200, 200);
    }

    public CanvasAdapter(int width, int height) {
        this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getCanvas() {
        return this.canvas;
    }

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }

    public void setAttribute(String attribute, int value) {
        if ("width".equals(attribute)) {
            this.setWidth(value);
        } else if ("height".equals(attribute)) {
            this.setHeight(value);
        }
    }

    public void setWidth(int width) {
        int height = canvas.getHeight();
        this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        initContextAdapter();
    }

    public void setHeight(int height) {
        int width = canvas.getWidth();
        this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        initContextAdapter();
    }

    public ContextAdapter getContext() {
        if (this.contextAdapter == null) {
            initContextAdapter();
        }
        return this.contextAdapter;
    }

    public void initContextAdapter() {
        Graphics2D context = (Graphics2D) canvas.getGraphics();
        context.setBackground(ColorsAdapter.TRANSPARENT);
        context.setColor(Color.BLACK); //默认绘图颜色设置为黑色
        context.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //默认的线条样式改变
        context.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));
        if (contextAdapter == null) {
            this.contextAdapter = new ContextAdapter(context, this.canvas);
        } else {
            this.contextAdapter.reset(context, this.canvas);
        }
    }
}
