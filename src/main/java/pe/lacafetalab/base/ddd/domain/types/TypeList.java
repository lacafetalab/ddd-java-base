package pe.lacafetalab.base.ddd.domain.types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import pe.lacafetalab.base.ddd.domain.exception.BadRequestException;

public abstract class TypeList<T> extends TypeBase<List<T>> {

	public TypeList() {
		super(new ArrayList<>());
	}

	public TypeList(List<T> values) {
		super(Optional.ofNullable(values).orElse(new ArrayList<>()));
	}

	public List<T> values() {
		return value();
	}

	public boolean isEmpty() {
		return values() == null || values().isEmpty();
	}

	public void verifyIsNotEmpty(BadRequestException ex) {
		if (isEmpty()) {
			throw ex;
		}
	}

	public boolean hasAnyRepeated() {
		if (isEmpty()) {
			return false;
		}
		Set<T> set = new HashSet<>();
		return values().stream().anyMatch(t -> !set.add(t));
	}

	public boolean areAllUnique() {
		if (isEmpty()) {
			return true;
		}
		Set<T> set = new HashSet<>();
		return values().stream().allMatch(t -> set.add(t));
	}

	public List<T> distinct() {
		return new ArrayList<>(new HashSet<>(values()));
	}

	public <R> List<R> asList(Function<T, R> f) {
		return values().stream().map(v -> f.apply(v)).collect(Collectors.toList());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values() == null) ? 0 : values().hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeList other = (TypeList) obj;
		if (values() == null) {
			if (other.values() != null)
				return false;
		} else if (!values().equals(other.values()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return values().toString();
	}
}
