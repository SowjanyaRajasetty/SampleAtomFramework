package resources;

import java.util.Locale;
import java.util.Map;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;

public class DataTableConfigurator implements TypeRegistryConfigurer {
	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry typeRegistry) {
		TableEntryTransformer<ResponseValidator> transformer = new TableEntryTransformer<ResponseValidator>() {

			@Override
			public ResponseValidator transform(Map<String, String> entry) throws Throwable {
				return new ResponseValidator(entry.get("element"), entry.get("matcher"), entry.get("value"), entry.get("type"));
			}

		};
		DataTableType tableType = new DataTableType(ResponseValidator.class, transformer);
		typeRegistry.defineDataTableType(tableType);
	}

}