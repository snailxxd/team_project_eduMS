/*package com.infoa.educationms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**") // 这个处理器匹配所有路径
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        // 如果请求路径以 "api/" 开头，这是一个API调用。
                        // 我们不应该为API调用返回静态资源或index.html。
                        // 返回 null 会让Spring MVC尝试其他处理器，比如处理@Controller或@RestController的映射。
                        if (resourcePath.startsWith("api/")) {
                            return null;
                        }

                        Resource requestedResource = location.createRelative(resourcePath);

                        // 如果请求的资源存在，则直接返回
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }

                        // 对于其他所有找不到的资源（并且不是API调用），则返回index.html，以支持SPA的前端路由。
                        // 这个逻辑需要确保只对前端路由的路径返回index.html，而不是对所有未找到的资源。
                        // 一个简单的判断是，如果路径不包含点（通常表示文件扩展名），则可能是前端路由。
                        // 或者，如果明确请求的是 .html 文件但未找到（除了index.html本身）。
                        // 注意：对于实际项目中更复杂的场景，可能需要更完善的判断逻辑。
                        // 例如，排除已知的静态资源目录如 /assets/, /images/ 等，即使它们下的文件未找到也不应返回index.html，而应404。
                        if (!resourcePath.contains(".") || resourcePath.endsWith(".html")) {
                            // 避免对已知的特定静态文件（如 favicon.ico）也错误地返回 index.html
                            if (!resourcePath.equals("favicon.ico") && !resourcePath.equals("vite.svg")) { // vite.svg 来自您的 index.html
                                return new ClassPathResource("/static/index.html");
                            }
                        }

                        // 如果以上条件都不满足（例如，请求一个不存在的带扩展名的静态资源），则返回null，这将导致404。
                        return null;
                    }
                });
    }
}*/