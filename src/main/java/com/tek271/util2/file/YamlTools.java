package com.tek271.util2.file;

import com.google.common.annotations.VisibleForTesting;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

public class YamlTools {
	@VisibleForTesting
	ResourceTools resourceTools = new ResourceTools();

	public Map<String, String> readFile(String fileName) {
		Map<String, String> result = new HashMap<>();
		InputStream inputStream = resourceTools.readAsInputStream(fileName);
		Yaml yaml = new Yaml();
		@SuppressWarnings("unchecked")
		Map<String, String> ym = (Map<String, String>) yaml.load(inputStream);
		if (ym==null) return result;

		ym.forEach((k,v) -> result.put(k, trimToEmpty(v)));
		return result;
	}


}
