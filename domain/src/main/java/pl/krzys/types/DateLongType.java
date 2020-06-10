package pl.krzys.types;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.BigIntTypeDescriptor;

import java.util.Date;

public class DateLongType extends AbstractSingleColumnStandardBasicType<Date> {
    public static final DateLongType INSTANCE = new DateLongType();
    public DateLongType() {
        super(BigIntTypeDescriptor.INSTANCE, DateLongJavaDescriptor.INSTANCE);

    }

    @Override
    public String getName() {
        return "DateLong";
    }

}