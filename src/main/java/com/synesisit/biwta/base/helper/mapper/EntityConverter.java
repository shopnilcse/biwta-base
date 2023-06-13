package com.synesisit.biwta.base.helper.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Tomal Mahdi
 * @since 01 January, 2021
 *
 */
public class EntityConverter {
    /**
     * Convert 'entity or DTO' using BeanUtils
     *
     * @param src - Object that will be converted to
     * @param target - target object
     * @return Object -It will return target object
     * */
    public static Object convert(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullAttribute(src));
        return  target;
    }

    public static String[] getNullAttribute (Object source) {
        final BeanWrapper wrapper = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor propertyDescriptor : descriptors) {
            Object srcValue = wrapper.getPropertyValue(propertyDescriptor.getName());
            if (srcValue == null) emptyNames.add(propertyDescriptor.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    /** Convert List to-and-from entity to DTO  using ModelMapper*/
    private static ModelMapper modelMapper = new ModelMapper();

    /**
     * Model mapper property setting are specified in the following block.
     * Default property matching strategy is set to Strict see {@link MatchingStrategies}
     * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>      type of result object.
     * @param <T>      type of source object to map from.
     * @param entity   entity that needs to be mapped.
     * @param outClass class of result object.
     * @return new object of <code>outClass</code> type.
     */
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> convertList(final Collection<T> sourceList, Class<D> destinationClass) {
        return (List<D>) sourceList.stream()
                .map(entity -> map(entity, destinationClass))
                .collect(Collectors.toList());
    }
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }


}
