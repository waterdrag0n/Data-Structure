public interface IAutocomplete {
	void construct(String[] s);

	String autocompletedWord(String s);

	float average();
}