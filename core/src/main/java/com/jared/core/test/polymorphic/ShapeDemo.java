package com.jared.core.test.polymorphic;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-12
 * Time: 下午7:28
 * To change this template use File | Settings | File Templates.
 */
public class ShapeDemo {
    public Shape createShape() {
        Random random = new Random();
        int r = random.nextInt(3);
        Shape shape = null;
        switch (r) {
            case 0:
                shape = new Rectangle();
                break;
            case 1:
                //shape = new Triangle();
                break;
            case 2:
                shape = new Circle();
                break;
        }
        return shape;
    }

    /**
     * 在Java中，多态性是依靠动态绑定实现的，即Java虚拟机在运行时确定要调用哪一个同名方法。
     * @param args
     */
    public static void main(String[] args) {
        ShapeDemo shapeDemo = new ShapeDemo();
        Shape[] shapes = new Shape[10];
        for (int i = 0; i < 10; i++) {
            Shape shape = shapeDemo.createShape();
            shapes[i] = shape;
        }
        for (Shape shape:shapes){
            shape.draw();
        }
    }
}
