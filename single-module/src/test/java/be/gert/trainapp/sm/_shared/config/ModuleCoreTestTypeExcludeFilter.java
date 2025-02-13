package be.gert.trainapp.sm._shared.config;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

public class ModuleCoreTestTypeExcludeFilter extends TypeExcludeFilter {
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {
		return metadataReader.getClassMetadata().getClassName().contains("._adapter");
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ModuleCoreTestTypeExcludeFilter;
	}

	@Override
	public int hashCode() {
		return 1;
	}
}
