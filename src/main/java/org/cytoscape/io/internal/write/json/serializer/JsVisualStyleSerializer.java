package org.cytoscape.io.internal.write.json.serializer;

import static org.cytoscape.io.internal.write.json.serializer.CytoscapeJsToken.*;

import java.awt.Color;
import java.io.IOException;

import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualStyle;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsVisualStyleSerializer extends JsonSerializer<VisualStyle> {

	@Override
	public void serialize(final VisualStyle vs, JsonGenerator jg, SerializerProvider provider) throws IOException,
			JsonProcessingException {

		jg.useDefaultPrettyPrinter();

		jg.writeStartObject();

		// Title of Visual Style
		jg.writeStringField(TITLE.toString(), vs.getTitle());

		// Mappings and Defaults are stored as array.
		jg.writeArrayFieldStart(STYLE.toString());

		// Node Mapping
		serializeNodeStyle(vs, jg);

		// Edge Mapping
		serializeEdgeStyle(vs, jg);

		// TODO What else?

		jg.writeEndArray();

		jg.writeEndObject();
	}

	private void serializeNodeStyle(final VisualStyle vs, JsonGenerator jg) throws IOException {
		jg.writeStartObject();
		jg.writeStringField(SELECTOR.toString(), NODE.toString());

		jg.writeObjectFieldStart(CSS.toString());
		jg.writeStringField(BACKGROUND_COLOR.toString(),
				decodeColor((Color) vs.getDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR)));
		jg.writeStringField(BORDER_COLOR.toString(),
				decodeColor((Color) vs.getDefaultValue(BasicVisualLexicon.NODE_BORDER_PAINT)));
		jg.writeNumberField(BORDER_WIDTH.toString(), vs.getDefaultValue(BasicVisualLexicon.NODE_BORDER_WIDTH));
		jg.writeStringField(SHAPE.toString(), vs.getDefaultValue(BasicVisualLexicon.NODE_SHAPE).getDisplayName()
				.toLowerCase());
		jg.writeEndObject();

		jg.writeEndObject();
	}

	private void createMapping() {
		// TODO implememt this
		// Passthrough
	}

	private void serializeEdgeStyle(final VisualStyle vs, JsonGenerator jg) throws IOException {
		jg.writeStartObject();
		jg.writeStringField(SELECTOR.toString(), EDGE.toString());

		jg.writeObjectFieldStart(CSS.toString());
		jg.writeNumberField(WIDTH.toString(), vs.getDefaultValue(BasicVisualLexicon.EDGE_WIDTH));
		jg.writeStringField(LINE_COLOR.toString(),
				decodeColor((Color) vs.getDefaultValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT)));
		jg.writeEndObject();

		jg.writeEndObject();
	}

	private final String decodeColor(Color color) {
		final StringBuilder builder = new StringBuilder();

		builder.append("#");
		builder.append(Integer.toHexString(color.getRGB()));

		return builder.toString();
	}

	@Override
	public Class<VisualStyle> handledType() {
		return VisualStyle.class;
	}
}