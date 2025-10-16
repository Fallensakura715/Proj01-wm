import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    public static void main(String[] args) {
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String JDBC_URL = "jdbc:mysql://localhost:3306/proj01?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
        final String JDBC_USERNAME = "root";
        final String JDBC_PASSWORD = "password";

        String projPath = System.getProperty("user.dir");
        String pojoPath = projPath + "/P01-pojo";
        String serverPath = projPath + "/P01-server";
        String commonPath = projPath + "/P01-common";

        Map<OutputFile, String> pathInfo = new HashMap<>();

        pathInfo.put(OutputFile.entity, pojoPath + "/src/main/java/com/fallensakura/entity");
        pathInfo.put(OutputFile.controller, serverPath + "/src/main/java/com/fallensakura/controller");
        pathInfo.put(OutputFile.mapper, serverPath + "/src/main/java/com/fallensakura/mapper");
        pathInfo.put(OutputFile.xml, serverPath + "/src/main/resources/mapper");
        pathInfo.put(OutputFile.service, serverPath + "/src/main/java/com/fallensakura/service");
        pathInfo.put(OutputFile.serviceImpl, serverPath + "/src/main/java/com/fallensakura/service/impl");

        FastAutoGenerator.create(JDBC_URL
                , JDBC_USERNAME
                , JDBC_PASSWORD)
                .globalConfig(builder -> {
                    builder.author("Fallensakura")
                            .enableSpringdoc();
                })

                .packageConfig(builder -> {
                    builder.parent("com.fallensakura")
                            .pathInfo(pathInfo);
                })

                .strategyConfig(builder -> {
                    builder.addInclude("address", "category", "dish"
                            , "employee", "flavor"
                            , "order", "order_detail"
                            , "setmeal", "shopping_cart"
                            , "user", "setmeal_dish")
                            .entityBuilder()
                            .enableLombok()
                            .enableSerialAnnotation()
                            .enableChainModel()
                            .controllerBuilder()
                            .enableRestStyle()
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .mapperBuilder()
                            .enableFileOverride();
                })

                .templateEngine(new VelocityTemplateEngine())
                .execute();

        System.out.println("Done!!!");

    }
}
