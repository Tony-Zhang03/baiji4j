package com.ctriposs.baiji.schema;

import com.ctriposs.baiji.exception.BaijiTypeException;
import com.ctriposs.baiji.util.ObjectUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

public class MapSchema extends UnnamedSchema {

    private final Schema _valueSchema;

    public static MapSchema createMap(Schema type) {
        return new MapSchema(type, null);
    }

    /**
     * Static function to return new instance of map schema
     *
     * @param jtok     JSON object for the map schema
     * @param props    list of named schemas already read
     * @param names    enclosing namespace of the map schema
     * @param encSpace
     * @return
     */
    static MapSchema newInstance(JsonNode jtok, PropertyMap props, SchemaNames names, String encSpace) {
        JsonNode valuesNode = jtok.get("values");
        if (valuesNode == null) {
            throw new BaijiTypeException("Map does not have 'values'");
        }

        return new MapSchema(parseJson(valuesNode, names, encSpace), props);
    }

    /**
     * Constructor for map schema class
     *
     * @param valueSchema schema for map values type
     * @param props
     */
    private MapSchema(Schema valueSchema, PropertyMap props) {
        super(SchemaType.MAP, props);
        if (null == valueSchema) {
            throw new IllegalArgumentException("valueSchema cannot be null.");
        }
        _valueSchema = valueSchema;
    }

    /**
     * @return Schema for map values type
     */
    public Schema getValueSchema() {
        return _valueSchema;
    }

    /**
     * Writes map schema in JSON format
     *
     * @param gen      JSON generator
     * @param names    list of named schemas already written
     * @param encSpace enclosing namespace of the map schema
     */
    @Override
    protected void writeJsonFields(JsonGenerator gen, SchemaNames names, String encSpace)
            throws IOException {
        gen.writeFieldName("values");
        _valueSchema.writeJson(gen, names, encSpace);
    }

    /**
     * Checks if this schema can read data written by the given schema. Used for decoding data.
     *
     * @param writerSchema The writer's schema to match against.
     * @return true if this and writer schema are compatible based on the Baiji specification, false otherwise
     */
    @Override
    public boolean canRead(Schema writerSchema) {
        if (writerSchema.getType() != getType()) {
            return false;
        }
        MapSchema that = (MapSchema) writerSchema;
        return that != null && _valueSchema.canRead(that._valueSchema);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MapSchema)) {
            return false;
        }
        MapSchema that = (MapSchema) obj;
        if (that == null) {
            return false;
        }
        return _valueSchema.equals(that._valueSchema) && ObjectUtils.equals(that.getProps(), getProps());
    }


    @Override
    public int hashCode() {
        return 29 * _valueSchema.hashCode() + ObjectUtils.hashCode(getProps());
    }
}
