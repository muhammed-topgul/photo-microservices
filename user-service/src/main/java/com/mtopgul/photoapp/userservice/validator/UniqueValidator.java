package com.mtopgul.photoapp.userservice.validator;

import com.mtopgul.photoapp.userservice.annotion.Unique;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author muhammed-topgul
 * @since 21/09/2023 10:02
 */
@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @PersistenceContext
    private EntityManager entityManager;
    private Class<?> entity;
    private String field;

    @Override
    public boolean isValid(String param, ConstraintValidatorContext constraintValidatorContext) {
        return isExist(String.format("select * from %s where %s = '%s'", tableName(), columnName(), param));
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
    }

    private String tableName() {
        Table table = entity.getAnnotation(Table.class);
        if (Objects.nonNull(table)) {
            return table.name().toUpperCase();
        } else {
            return entity.getSimpleName().toUpperCase();
        }
    }

    private String columnName() {
        for (Field declaredField : entity.getDeclaredFields()) {
            if (declaredField.getName().equals(field)) {
                if (declaredField.isAnnotationPresent(Column.class)) {
                    String name = declaredField.getAnnotation(Column.class).name();
                    if (!name.isBlank() && !name.isEmpty()) {
                        return name.toUpperCase();
                    }
                }
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < field.length(); i++) {
                    char currentChar = field.charAt(i);
                    if (Character.isUpperCase(currentChar)) {
                        output.append("_");
                    }
                    output.append(currentChar);
                }
                return output.toString().toUpperCase();
            }
        }
        throw new UnsupportedOperationException(String.format("%s.%s not exist!", entity.getSimpleName(), field));
    }

    private boolean isExist(String query) {
        return entityManager.createNativeQuery(query)
                .getResultList()
                .stream()
                .findAny()
                .isEmpty();
    }
}
