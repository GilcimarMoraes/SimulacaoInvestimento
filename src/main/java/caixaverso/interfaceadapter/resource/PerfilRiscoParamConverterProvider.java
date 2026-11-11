package caixaverso.interfaceadapter.resource;

import caixaverso.domain.enums.PerfilRiscoEnum;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Locale;

/**
 * Aceita o perfil em qualquer caixa no path, por exemplo:
 * /produtos-recomendados/Moderado, /produtos-recomendados/MODERADO ou /moderado.
 */
@Provider
public class PerfilRiscoParamConverterProvider implements ParamConverterProvider {

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType != PerfilRiscoEnum.class) {
            return null;
        }
        return (ParamConverter<T>) new ParamConverter<PerfilRiscoEnum>() {
            @Override
            public PerfilRiscoEnum fromString(String value) {
                if (value == null) {
                    return null;
                }
                try {
                    return PerfilRiscoEnum.valueOf(value.trim().toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException e) {
                    throw new NotFoundException("Perfil de risco inválido: " + value);
                }
            }

            @Override
            public String toString(PerfilRiscoEnum value) {
                return value == null ? null : value.name();
            }
        };
    }
}
