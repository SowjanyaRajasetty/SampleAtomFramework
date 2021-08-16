package resources;

/**
 * POJO used to define contents of cucumber datatable when invoking response
 * body validation of rest api calls
 */
public class ResponseValidator {
	private String element;
	private String matcher;
	private String value;
	private String type;

	public ResponseValidator(){

	}

	public ResponseValidator(String element, String matcher, String value, String type){
		this.element = element;
		this.matcher = matcher;
		this.value = value;
		this.type = type;
	}

	public String getElement() {
		return element;
	}

	public String getMatcher() {
		return matcher;
	}

	public String getValue() {
		return value;
	}

	public String getType() {
		return type;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public void setMatcher(String matcher) {
		this.matcher = matcher;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return String.format("element = %s matcher = %s value = %s type = %s", this.element,this.matcher,this.value,this.type);		
	}
}