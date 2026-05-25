package com.inventory.inventory_backend.config;

import com.inventory.inventory_backend.entity.*;
import com.inventory.inventory_backend.enums.MovementType;
import com.inventory.inventory_backend.enums.Role;
import com.inventory.inventory_backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;


@Configuration
@RequiredArgsConstructor
@Slf4j  // Lombok: genera un logger llamado `log`
public class DataSeeder {
    @Bean
    CommandLineRunner seedData(
            CategoryRepository categories,
            SupplierRepository suppliers,
            ProductRepository products,
            StockMovementRepository movements,
            UserRepository users,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Sólo insertar si la base está vacía
            if (products.count() > 0) {
                log.info("Base de datos ya tiene datos, omitiendo seed.");
                return;
            }

            log.info("Insertando datos de prueba...");

            // Categorías
            Category electronica = categories.save(Category.builder()
                    .name("Electrónica").description("Dispositivos electrónicos y gadgets").build());
            Category papeleria = categories.save(Category.builder()
                    .name("Papelería").description("Artículos de oficina y escritura").build());

            // Proveedores
            Supplier techCorp = suppliers.save(Supplier.builder()
                    .name("TechCorp S.A.").contactName("Juan Pérez")
                    .email("juan@techcorp.com").phone("555-0100").build());
            Supplier officeMax = suppliers.save(Supplier.builder()
                    .name("OfficeMax Colombia").contactName("Ana Gómez")
                    .email("ana@officemax.co").phone("555-0200").build());

            // Productos
            Product laptop = products.save(Product.builder()
                    .name("Laptop Dell Inspiron 15").sku("DELL-INS-15")
                    .price(new BigDecimal("2499000")).stockQuantity(8).minStockAlert(3)
                    .category(electronica).supplier(techCorp).build());

            Product mouse = products.save(Product.builder()
                    .name("Mouse Inalámbrico Logitech").sku("LOG-MOUSE-01")
                    .price(new BigDecimal("89000")).stockQuantity(2).minStockAlert(5) // stock bajo!
                    .category(electronica).supplier(techCorp).build());

            Product resma = products.save(Product.builder()
                    .name("Resma Papel Carta").sku("PAPEL-CARTA-500")
                    .price(new BigDecimal("15000")).stockQuantity(50).minStockAlert(10)
                    .category(papeleria).supplier(officeMax).build());

            // Movimientos de stock
            movements.save(StockMovement.builder()
                    .type(MovementType.ENTRADA).quantity(10).product(laptop)
                    .notes("Compra inicial").build());
            movements.save(StockMovement.builder()
                    .type(MovementType.SALIDA).quantity(2).product(laptop)
                    .notes("Venta a cliente #001").build());
            movements.save(StockMovement.builder()
                    .type(MovementType.ENTRADA).quantity(7).product(mouse)
                    .notes("Compra inicial").build());
            movements.save(StockMovement.builder()
                    .type(MovementType.SALIDA).quantity(5).product(mouse)
                    .notes("Venta a cliente #002").build());

            // Usuario admin de prueba (en semana 5 agregaremos bcrypt)
            users.save(User.builder()
                    .username("admin").email("admin@inventory.com")
                    .password(passwordEncoder.encode("admin123")).role(Role.ADMIN).build());
            users.save(User.builder()
                    .username("empleado1").email("empleado1@inventory.com")
                    .password(passwordEncoder.encode("emp123")).role(Role.EMPLOYEE).build());

            log.info("✅ Datos de prueba insertados correctamente.");
        };
    }
}
