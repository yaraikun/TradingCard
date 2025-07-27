# 🎴 TCIS - Trading Card Information System

**TCIS (Trading Card Information System)** is a console-based Java application that allows users to manage and organize their trading card collections. Think of it as *Pikachu* — but a tad bit more complicated.

---

```mermaid
---
config:
  layout: elk
  elk:
    mergeEdges: false
    nodePlacementStrategy: LINEAR_SEGMENTS
  theme: redux
  look: neo
  class:
    hideEmptyMembersBox: true
---
classDiagram
direction TB
    class InventorySystem {
	    - totalMoney: double
	    - collectionManager: CollectionManager
	    - binderManager: BinderManager
	    - deckManager: DeckManager
	    + InventorySystem()
	    + run() void
	    + getTotalMoney() double
	    + sellCardFromCollection(cardName: String) boolean
		+ sellBinder(binderName: String) boolean
	    + sellDeck(deckName: String) boolean
	    + createBinder(name: String, type: String) boolean
	    + deleteBinder(name: String) boolean
	    + getBinders() ArrayList~Binder~
	    + findBinder(name: String) Binder
	    + addCardToBinder(cardName: String, binderName: String) int
	    + removeCardFromBinder(cardIndex: int, binderName: String) boolean
	    + performTrade(binderName: String, cardIndex: int, incomingCard: Card) boolean
	    + createDeck(name: String, type: String) boolean
	    + deleteDeck(name: String) boolean
	    + getDecks() ArrayList~Deck~
	    + findDeck(name: String) Deck
		+ addCardToDeck(c: String, d: String) int
		+ removeCardFromDeck(i: int, n: String) boolean
		+ addNewCard(n: String, v: double, r: Rarity, t: Variant) boolean
	    + findCard(name: String) Card
	    + increaseCardCount(name: String, amount: int) boolean
	    + decreaseCardCount(name: String, amount: int) boolean
	    + getCardTypes() ArrayList~Card~
	    + getCardCounts() HashMap~String, Integer~
		+ isCardAvailable(name: String) boolean
    }
    class CollectionManager {
	    - cardTypes: ArrayList~Card~
	    - cardCounts: HashMap~String, Integer~
	    + CollectionManager()
	    + findCard(name: String) Card
	    + addNewCard(name: String, baseValue: double, rarity: Rarity, variant: Variant) boolean
	    + increaseCount(name: String, amount: int) boolean
	    + decreaseCount(name: String, amount: int) boolean
	    + sellCard(cardName: String) boolean
	    + isCardAvailable(name: String) boolean
	    + getCardTypes() ArrayList~Card~
	    + getCardCounts() HashMap~String, Integer~
    }
    class BinderManager {
	    - binders: ArrayList~Binder~
	    - collectionManager: CollectionManager
	    + BinderManager(cm: CollectionManager)
	    + findBinder(name: String) Binder
	    + createBinder(name: String, type: String) boolean
	    + deleteBinder(name: String) boolean
	    + sellBinder(name: String) double
	    + addCardToBinder(cardName: String, binderName: String) int
	    + removeCardFromBinder(cardIndex: int, binderName: String) boolean
	    + performTrade(binderName: String, cardIndex: int, incomingCard: Card) boolean
	    + getBinders() ArrayList~Binder~
    }
    class DeckManager {
	    - decks: ArrayList~Deck~
	    - collectionManager: CollectionManager
	    + DeckManager(cm: CollectionManager)
	    + findDeck(name: String) Deck
	    + createDeck(name: String, type: String) boolean
	    + deleteDeck(name: String) boolean
	    + sellDeck(name: String) double
	    + addCardToDeck(cardName: String, deckName: String) int
	    + removeCardFromDeck(cardIndex: int, deckName: String) boolean
	    + getDecks() ArrayList~Deck~
    }
    class MainFrame {
	    - inventory: InventorySystem
	    - cardLayout: CardLayout
	    - mainPanel: JPanel
		- totalMoneyLabel: JLabel
	    - mainMenuPanel: MainMenuPanel
	    - collectionPanel: CollectionPanel
	    - binderPanel: BinderPanel
	    - deckPanel: DeckPanel
	    - binderContentsPanel: BinderContentsPanel
	    - deckContentsPanel: DeckContentsPanel
	    + MainFrame(inventory: InventorySystem)
	    + showPanel(panelName: String) void
	    + showBinderContents(binderName: String) void
	    + showDeckContents(deckName: String) void
	    + updateTotalMoney() void
    }
    class MainMenuPanel {
	    - mainFrame: MainFrame
	    + MainMenuPanel(mainFrame: MainFrame)
    }
    class CollectionPanel {
	    - inventory: InventorySystem
	    - mainFrame: MainFrame
	    - cardListModel: DefaultListModel
	    - cardList: JList~String~;
        - sellCardButton: JButton;
	    - updateCountButton: JButton;
	    - viewDetailsButton: JButton;
	    + CollectionPanel(mainFrame: MainFrame, inventory: InventorySystem)
	    + refreshView() void
	    - updateButtonStates() void
	    - handleAddCard() void
	    - handleViewDetails() void
	    - handleUpdateCount() void
	    - handleSellCard() void
    }
    class BinderPanel {
	    - inventory: InventorySystem
	    - mainFrame: MainFrame
	    - binderListModel: DefaultListModel
	    - binderList: JList~String~;
	    - viewButton: JButton;
	    - sellButton: JButton;
	    - deleteButton: JButton;
	    + BinderPanel(mainFrame: MainFrame, inventory: InventorySystem)
	    + refreshView() void
	    - updateButtonStates() void
	    - handleCreate() void
	    - handleView() void
	    - handleDelete() void
	    - handleSell() void
    }
    class DeckPanel {
	    - inventory: InventorySystem
	    - mainFrame: MainFrame
	    - deckListModel: DefaultListModel
	    - deckList: JList~String~;
	    - viewButton: JButton;
	    - sellButton: JButton;
	    - deleteButton: JButton;
	    + DeckPanel(mainFrame: MainFrame, inventory: InventorySystem)
	    + refreshView() void
	    - updateButtonStates() void
	    - handleCreate() void
	    - handleView() void
	    - handleDelete() void
	    - handleSell() void
    }
    class BinderContentsPanel {
	    - inventory: InventorySystem
	    - mainFrame: MainFrame
	    - currentBinder: Binder
	    - binderListModel: DefaultListModel~String~;
	    - binderCardList: JList~String~;
	    - collectionListModel: DefaultListModel~String~;
	    - collectionCardList: JList~String~;
	    - addCardButton: JButton;
	    - removeCardButton: JButton;
	    - tradeButton: JButton;
	    - setPriceButton: JButton;
	    - binderTitleLabel: JLabel;
	    + BinderContentsPanel(mainFrame: MainFrame, inventory: InventorySystem)
	    + loadBinder(binderName: String) void
	    - refreshView() void
	    - updateButtonStates() void
	    - handleAddCard() void
	    - handleRemoveCard() void
	    - handleTrade() void
	    - handleSetPrice() void
    }
    class DeckContentsPanel {
	    - inventory: InventorySystem
	    - mainFrame: MainFrame
	    - currentDeck: Deck
	    - deckListModel: DefaultListModel~String~;
	    - deckCardList: JList~String~;
	    - collectionListModel: DefaultListModel~String~;
	    - collectionCardList: JList~String~;
	    - deckTitleLabel: JLabel;
	    + DeckContentsPanel(mainFrame: MainFrame, inventory: InventorySystem)
	    + loadDeck(deckName: String) void
	    - refreshView() void
	    - handleAddCard() void
	    - handleRemoveCard() void
    }
    class Card {
	    - name: String
	    - baseValue: double
	    - rarity: Rarity
	    - variant: Variant
	    + Card(name: String, baseValue: double, rarity: Rarity, variant: Variant)
	    + getName() String
	    + getBaseValue() double
	    + getRarity() Rarity
	    + getVariant() Variant
	    + getCalculatedValue() double
    }
    class Rarity {
	    - displayName: String
	    + COMMON$
	    + UNCOMMON$
	    + RARE$
	    + LEGENDARY$
	    - Rarity(displayName: String)
	    + getDisplayName() String
	    + fromInt(choice: int) Rarity$
    }
    class Variant {
	    - displayName: String
	    - multiplier: double
	    + NORMAL$
	    + EXTENDED_ART$
	    + FULL_ART$
	    + ALT_ART$
	    - Variant(displayName: String, multiplier: double)
	    + getDisplayName() String
	    + getMultiplier() double
	    + fromInt(choice: int) Variant$
    }
    class Deck {
	    + MAX_CAPACITY: int$
	    # name: String
	    # cards: ArrayList~Card~
	    + Deck(name: String)
	    + getName() String
	    + getCards() ArrayList~Card~
	    + getCardCount() int
	    + isFull() boolean
	    + containsCard(cardName: String) boolean
	    + addCard(card: Card) boolean
	    + removeCard(index: int) Card
	    + isSellable() boolean*
    }
    class NormalDeck {
	    + NormalDeck(name: String)
	    + isSellable() boolean
    }
    class SellableDeck {
	    + SellableDeck(name: String)
	    + isSellable() boolean
    }
    class Binder {
	    + MAX_CAPACITY: int$
	    # name: String
	    # cards: ArrayList~Card~
	    + Binder(name: String)
	    + getName() String
	    + getCards() ArrayList~Card~
	    + getCardCount() int
	    + isFull() boolean
	    + addCard(card: Card) boolean
	    + removeCard(index: int) Card
	    + canAddCard(card: Card) boolean*
	    + isSellable() boolean*
	    + canTrade() boolean*
	    + calculatePrice() double*
    }
    class NonCuratedBinder {
	    + NonCuratedBinder(name: String)
	    + canAddCard(card: Card) boolean
	    + isSellable() boolean
	    + canTrade() boolean
	    + calculatePrice() double
    }
    class CollectorBinder {
	    + CollectorBinder(name: String)
	    + canAddCard(card: Card) boolean
	    + isSellable() boolean
	    + canTrade() boolean
	    + calculatePrice() double
    }
    class SellableBinder {
	    + SellableBinder(name: String)
	    + isSellable() boolean
	    + canTrade() boolean
    }
    class PauperBinder {
	    + PauperBinder(name: String)
	    + canAddCard(card: Card) boolean
	    + calculatePrice() double
    }
    class RaresBinder {
	    + RaresBinder(name: String)
	    + canAddCard(card: Card) boolean
	    + calculatePrice() double
    }
    class LuxuryBinder {
	    - customPrice: double
	    + LuxuryBinder(name: String)
	    + canAddCard(card: Card) boolean
	    + calculatePrice() double
	    + setPrice(price: double) boolean
    }
	<<Facade>> InventorySystem
	<<JFrame>> MainFrame
	<<JPanel>> MainMenuPanel
	<<JPanel>> CollectionPanel
	<<JPanel>> BinderPanel
	<<JPanel>> DeckPanel
	<<JPanel>> BinderContentsPanel
	<<JPanel>> DeckContentsPanel
	<<Enum>> Rarity
	<<Enum>> Variant
	<<Abstract>> Deck
	<<Abstract>> Binder
	<<Abstract>> SellableBinder

%% Controller Layer
InventorySystem *-- "1" CollectionManager : has
InventorySystem *-- "1" BinderManager : has
InventorySystem *-- "1" DeckManager : has

%% Backend Layer
BinderManager ..> "1" CollectionManager : uses
DeckManager ..> "1" CollectionManager : uses

%% GUI Layer
InventorySystem ..> "1" MainFrame : uses

MainFrame *-- "1" MainMenuPanel : contains
MainFrame *-- "1" CollectionPanel : contains
MainFrame *-- "1" BinderPanel : contains
MainFrame *-- "1" BinderContentsPanel : contains
MainFrame *-- "1" DeckPanel : contains
MainFrame *-- "1" DeckContentsPanel : contains

MainMenuPanel ..> "1" MainFrame : uses
CollectionPanel ..> "1" MainFrame : uses
BinderPanel ..> "1" MainFrame : uses
BinderContentsPanel ..> "1" MainFrame : uses
DeckPanel ..> "1" MainFrame : uses
DeckContentsPanel ..> "1" MainFrame : uses

CollectionPanel ..> "1" InventorySystem : uses
BinderPanel ..> "1" InventorySystem : uses
BinderContentsPanel ..> "1" InventorySystem : uses
DeckPanel ..> "1" InventorySystem : uses
DeckContentsPanel ..> "1" InventorySystem : uses

%% GUI to Model Dependencies
CollectionPanel ..> Card : displays
CollectionPanel ..> Rarity : uses for creation
CollectionPanel ..> Variant : uses for creation
BinderPanel ..> Binder : displays
DeckPanel ..> Deck : displays
BinderContentsPanel ..> Card : displays and manipulates
BinderContentsPanel ..> Rarity : uses for trade
BinderContentsPanel ..> Variant : uses for trade
DeckContentsPanel ..> Card : displays and manipulates

%% Model Layer (Inheritance)
Deck <|-- NormalDeck : extends
Deck <|-- SellableDeck : extends
Binder <|-- NonCuratedBinder : extends
Binder <|-- CollectorBinder : extends
Binder <|-- SellableBinder : extends
SellableBinder <|-- PauperBinder : extends
SellableBinder <|-- RaresBinder : extends
SellableBinder <|-- LuxuryBinder : extends

%% Model Layer (Composition and Aggregation)
Card *-- "1" Rarity : has
Card *-- "1" Variant : has

CollectionManager o-- "0..*" Card : manages
BinderManager o-- "0..*" Binder : manages
DeckManager o-- "0..*" Deck : manages

Binder o-- "0..20" Card : contains
Deck o-- "0..10" Card : contains
```

---

## 🧰 Resources

### 🔗 Useful Links

* **UML Diagram (.drawio)**

---

## ✍️ Coding Conventions

Always rebase!

### 📦 Commit Message Format

Please follow the conventional commit format for clean and meaningful version history:

```
<type>: <subject>

[optional body]

[optional footer]
```

### 🏷️ Commit Types

| Type       | Description                                                                   |
| ---------- | ----------------------------------------------------------------------------- |
| `feat`     | A new feature                                                                 |
| `fix`      | A bug fix                                                                     |
| `docs`     | Documentation-only changes                                                    |
| `style`    | Code style changes (formatting, indentation, etc.)                            |
| `refactor` | Code changes that improve structure but don't change functionality            |
| `test`     | Adding or modifying tests                                                     |
| `chore`    | Maintenance tasks that don’t directly affect the codebase (e.g. `.gitignore`) |

### 💡 Examples

```
feat: implement addToDeck feature
fix: correct deleteDeck functionality
style: clean up trailing whitespaces
chore: update .gitignore
```

---

## 📌 MCO1 Checklist

Below are the deliverables and tasks associated with this project:

* [x] UML Class Diagram
* [x] ZIP file of the implemented code (with Javadoc documentation)
* [x] Test Script
* [x] Declaration of Original Work
