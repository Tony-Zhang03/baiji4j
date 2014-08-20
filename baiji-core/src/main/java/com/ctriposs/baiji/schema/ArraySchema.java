package com.ctriposs.baiji.schema;

import com.ctriposs.baiji.exception.BaijiTypeException;
import com.ctriposs.baiji.util.ObjectUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;

public class ArraySchema extends UnnamedSchema {

    private final Schema _itemSchema;

    public ArraySchema(Schema itemSchema, PropertyMap props) {
        super(SchemaType.ARRAY, props);
        if (itemSchema == null) {
            throw new IllegalArgumentException("itemSchema cannot be null.");
        }
        _itemSchema = itemSchema;
    }

    public Schema getItemSchema() {
        return _itemSchema;
    }

    /**
     * Static class to return a new instance of ArraySchema
     *
     * @param node     JSON object for the array schema
     * @param props
     * @param names    list of named schemas already parsed
     * @param encSpace enclosing namespace for the array schema
     * @return
     */
    static ArraySchema newInstance(JsonNode node, PropertyMap props, SchemaNames names,
                                   String encSpace) {
        JsonNode itemsNode = node.get("items");
        if (itemsNode == null) {
            throw new BaijiTypeException("Array does not have 'items'.");
        }
        return new ArraySchema(parseJson(itemsNode, names, encSpace), props);
    }

    /**
     * Writes the array schema in JSON format
     *
     * @param gen      JSON generator
     * @param names    list of named schemas already written
     * @param encSpace enclosing namespace
     * @throws IOException
     */
    @Override
    protected void writeJsonFields(JsonGenerator gen, SchemaNames names, String encSpace)
            throws IOException {
        gen.writeFieldName("items");
        _itemSchema.writeJson(gen, names, encSpace);
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
        ArraySchema that = (ArraySchema) writerSchema;
        return that != null && _itemSchema.canRead(that._itemSchema);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ArraySchema) {
            ArraySchema that = (ArraySchema) obj;
            if (_itemSchema.equals(that._itemSchema)) {
                return ObjectUtils.equals(getProps(), that.getProps());
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 29 * _itemSchema.hashCode() + ObjectUtils.hashCode(getProps());
    }
}
