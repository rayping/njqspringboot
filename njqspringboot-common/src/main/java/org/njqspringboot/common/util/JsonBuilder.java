package org.njqspringboot.common.util;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonBuilder {

	public static final GsonBuilder INSTANCE = new GsonBuilder();

	static {
		INSTANCE.disableHtmlEscaping();
		INSTANCE.registerTypeAdapter(Date.class, new JsonDateDeserializer())
		.registerTypeAdapter(Date.class, new JsonDateSerializer());
	}

	public static Gson create() {
		return INSTANCE.create();
	}

//	public static void main(String[] args) {
//		String json = "{\"repaymentType\":null,\"loanLimit\":1.00,\"createdTime\":\"2015-07-23 21:01:57\",\"yearRate\":null,\"loanPeriod\":1,\"status\":\"1\"}";
//		LoanInfo l = create().fromJson(json, LoanInfo.class);
//		System.out.println(l.getCreatedTime());
//	}

	private static class JsonDateDeserializer implements JsonDeserializer<Date> {
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String s = json.getAsJsonPrimitive().getAsString();
			if(NumberUtils.isDigits(s)){
				BigDecimal b = new BigDecimal(s);
				Date d = new Date(b.longValue());
				return d;				
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
				try {
					return sdf.parse(json.getAsString());
				} catch (ParseException e) {
					return null;
				}
			}
		}
	}
	
	/**
	 * 序列化成从1970到现在毫秒数:
	 */
	private static class JsonDateSerializer implements JsonSerializer<Date>{

		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.getTime());
		}
		
	}
}
