package org.s23m.cell.generator;

import java.util.List;

import org.s23m.cell.Set;

public interface ModelToTextTransformation {

	CharSequence invoke(List<Set> parameters);
}