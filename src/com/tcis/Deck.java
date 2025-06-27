/**
 * Represents a trading binder that can hold a fixed number of cards.
 * 
 * Note:
 *     Decks do not allow duplicate cards.
 */
public class Deck {

    // --- Constants ---

    public static final int MAX_CAPACITY = 20;

    // --- Properties ---

    private final String name;
    private final Card[] cards; 
    private int cardCount;      

    // --- Constructors ---

    /**
     * Constructs a new, empty Deck with a given name.
     *
     * @param name The name of the Deck.
     */
    public Deck(String name) {
        this.name = name;
        this.cards = new Card[MAX_CAPACITY];
        this.cardCount = 0;
    }

    // --- Getters ---

    public String getName() {
        return this.name;
    }

    public int getCardCount() {
        return this.cardCount;
    }

    /**
     * Returns a copy of the cards array.
     * Defensively copies to prevent unintentional modification.
     *
     * @return A new array containing the cards currently in the binder.
     */
    public Card[] getCardsForDisplay() {
        Card[] displayCards = new Card[this.cardCount];
        System.arraycopy(this.cards, 0, displayCards, 0, this.cardCount);
        return displayCards;
    }

    // --- Methods ---

    /**
     * Adds a card to the deck if there is space.
     *
     * @param card The card to add.
     * @return true if the card was added successfully, false otherwise.
     */
    public boolean addCard(Card card) {
        if (isFull()) {
            return false;
        }

        this.cards[this.cardCount] = card;
        this.cardCount++;

        return true;
    }

    /**
     * Pops a card from the deck at a specific index.
     * Shifts all elements to the right of the index, to the left.
     *
     * @param index The index of the card to remove.
     * @return The card that was removed, or null if the index is invalid.
     */
    public Card removeCardAtIndex(int index) {
        if (index < 0 || index >= this.cardCount) {
            return null; 
        }

        Card removedCard = this.cards[index];
        
        for (int i = index; i < this.cardCount - 1; i++) {
            this.cards[i] = this.cards[i + 1];
        }

        this.cards[this.cardCount - 1] = null; 
        this.cardCount--;

        return removedCard;
    }

    /**
     * Checks if deck is full.
     *
     * @return true if the card count has reached max capacity, false 
     *         otherwise.
     */
    public boolean isFull() {
        // Ain't no way i just unironically did the meme if true return true.
        // return (this.cardCount >= MAX_CAPACITY) ? true : false;
        return this.cardCount >= MAX_CAPACITY;
    }
}

