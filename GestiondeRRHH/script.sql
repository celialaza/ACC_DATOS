-- 1. CREAR BASE DE DATOS
CREATE DATABASE IF NOT EXISTS company_db;
USE company_db;

-- 2. TABLA DEPARTAMENTO (El "padre" de la relación)
CREATE TABLE department (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            location VARCHAR(100) -- Ejemplo: "Madrid", "Planta 3", "Remoto"
);

-- 3. TABLA EMPLEADO (El "hijo" de la relación)
CREATE TABLE employee (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          full_name VARCHAR(100) NOT NULL,
                          position VARCHAR(100), -- Cargo: "Desarrollador", "Gerente", etc.
                          salary DOUBLE,         -- Usamos DOUBLE para decimales monetarios
                          department_id INT,     -- Clave foránea

    -- Relación: Si borras un departamento, se borran sus empleados (CASCADE)
                          CONSTRAINT fk_department
                              FOREIGN KEY (department_id)
                                  REFERENCES department(id)
                                  ON DELETE CASCADE
);

-- 4. INSERTAR DATOS DE PRUEBA

-- Insertar Departamentos
INSERT INTO department (name, location) VALUES
                                            ('Desarrollo', 'Edificio Norte - Planta 2'),
                                            ('Marketing', 'Edificio Sur - Planta 1'),
                                            ('Recursos Humanos', 'Edificio Central');

-- Insertar Empleados
-- Notarás que usamos los IDs 1, 2 y 3 que corresponden a los departamentos de arriba.

-- Empleados de Desarrollo (ID 1)
INSERT INTO employee (full_name, position, salary, department_id) VALUES
                                                                      ('Ana García', 'Senior Java Developer', 45000.00, 1),
                                                                      ('Luis Rodríguez', 'Junior Frontend', 24000.50, 1),
                                                                      ('Carlos Tech', 'DevOps Engineer', 38000.00, 1);

-- Empleados de Marketing (ID 2)
INSERT INTO employee (full_name, position, salary, department_id) VALUES
                                                                      ('Marta López', 'Social Media Manager', 30000.00, 2),
                                                                      ('Jorge Sales', 'Director de Ventas', 55000.00, 2);

-- Empleados de RRHH (ID 3)
INSERT INTO employee (full_name, position, salary, department_id) VALUES
    ('Elena Boss', 'Jefa de Personal', 60000.00, 3);