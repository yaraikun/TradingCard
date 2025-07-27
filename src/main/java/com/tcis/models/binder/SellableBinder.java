package com.tcis.models.binder;

/**
 * An abstract class that serves as the base for all binder types that can be
 * sold.
 *
 * <p>It provides default implementations for {@code isSellable} (always true)
 * and {@code canTrade} (always false), as specified in the MCO2 requirements.
 * Using {@code final} prevents subclasses from overriding these fundamental
 * behaviors.</p>
 */
public abstract class SellableBinder extends Binder {

    /**
     * Constructs a new SellableBinder. This constructor is called by all
     * concrete sellable binder subclasses.
     *
     * @param name The name for the binder.
     */
    public SellableBinder(String name) {
        super(name);
    }

    /**
     * Specifies if this binder type can be sold.
     *
     * @return true for all subclasses of SellableBinder. This method is final
     *         and cannot be overridden.
     */
    public final boolean isSellable() {
        return true;
    }

    /**
     * Specifies if this binder type can be used for trading.
     *
     * @return false for all subclasses of SellableBinder. This method is final
     *         and cannot be overridden.
     */
    public final boolean canTrade() {
        return false;
    }
}
