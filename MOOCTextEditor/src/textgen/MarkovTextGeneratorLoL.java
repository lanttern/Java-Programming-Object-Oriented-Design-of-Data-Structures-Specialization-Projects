package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		/* Algorithm
		 *set "starter" to be the first word in the text  
          set "prevWord" to be starter
          for each word "w" in the source text starting at the second word
          check to see if "prevWord" is already a node in the list
          if "prevWord" is a node in the list
          add "w" as a nextWord to the "prevWord" node
          else
          add a node to the list with "prevWord" as the node's word
          add "w" as a nextWord to the "prevWord" node
          set "prevWord" = "w"
          add starter to be a next word for the last word in the source text.
		 */
		String word = "";
		if (sourceText.equals("")) return;
		else{
			String[] sourceWords = sourceText.split("[ ]+");
			starter = sourceWords[0];
			String preWord = starter;
			for (int i = 1; i < sourceWords.length + 1; i++){
				if (i < sourceWords.length){
					word = sourceWords[i];
				}
				else word = starter;
				
				boolean isNode = false;
				for (ListNode node: wordList){
					if (node.getWord().equals(preWord)){
						node.addNextWord(word);
						isNode = true;
					}
				}
				if (!isNode) {
					ListNode newNode = new ListNode(preWord);
					newNode.addNextWord(word);
					wordList.add(newNode);
				}
				preWord = word;
			}
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		/*
		 * set "currWord" to be the starter word
           set "output" to be ""
           add "currWord" to output
           while you need more words
           find the "node" corresponding to "currWord" in the list
           select a random word "w" from the "wordList" for "node"
           add "w" to the "output"
           set "currWord" to be "w" 
           increment number of words added to the list
		 */
		String currWord = starter;
		String output = "";
		String word = "";
		if (wordList.isEmpty() || numWords == 0) return output;
		else {
			output += currWord;
			for (int num = 1; num < numWords; num++){
				//System.out.println(currWord);
				for (ListNode node: wordList){
					if (node.getWord().equals(currWord)){
						word = node.getRandomNextWord(rnGenerator);
					}
				}
				currWord = word;
				output += " ";
				output += currWord;
			}
		}
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList.clear();
		this.train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		gen.retrain("");
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		//System.out.println(textString2);
		//gen.retrain(textString2);
		//System.out.println(gen);
		//System.out.println(gen.generateText(20));
		//gen.retrain("");
		//System.out.println(gen);
		//System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int index = generator.nextInt(nextWords.size());
	    return nextWords.get(index);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


