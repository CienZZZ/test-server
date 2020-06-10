package pl.krzys.types;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

import java.math.BigInteger;
import java.util.Date;

public class DateLongJavaDescriptor extends AbstractTypeDescriptor<Date> {
    public static final DateLongJavaDescriptor INSTANCE = new DateLongJavaDescriptor();
    public DateLongJavaDescriptor() {
        super(Date.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public <X> X unwrap(Date value, Class<X> type, WrapperOptions options) {
        if(value == null) return null;
        if(Long.class.isAssignableFrom(type))
            return (X) Long.valueOf(value.getTime());
        throw unknownUnwrap(type);
    }

    @Override
    public Date fromString(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <X> Date wrap(X value, WrapperOptions arg1) {
        if(value == null) return null;
        if(BigInteger.class.isInstance(value))
            return new Date(((BigInteger)value).longValue());
        if(Long.class.isInstance(value))
            return new Date((Long)value);
        if(Float.class.isInstance(value))
            return new Date((Long)value);
        throw unknownWrap(value.getClass());
    }
}