package com.nayaverdier.enviousapi.cooldowns;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Cooldowns
{
    private Map<Object, Long> cooldowns;
    private TimeUnit defaultUnit;

    /**
     * Create a new Cooldowns with the default TimeUnit
     *
     * @param defaultUnit The default TimeUnit for the class
     */
    public Cooldowns(TimeUnit defaultUnit)
    {
        this.cooldowns = new HashMap<>();
        this.defaultUnit = defaultUnit;
    }

    /**
     * Add a cooldown for specified object
     *
     * @param object The object to add a cooldown for
     * @param time The time, in the default TimeUnit, for the cooldown to be
     */
    public void addCooldown(Object object, long time)
    {
        addCooldown(object, time, defaultUnit);
    }

    /**
     * Add a cooldown for specified object
     *
     * @param object The object to add a cooldown for
     * @param time The time, in specified TimeUnit, for the cooldown to be
     * @param unit The TimeUnit for the time to be in
     */
    public void addCooldown(Object object, long time, TimeUnit unit)
    {
        cooldowns.put(object, TimeUnit.MILLISECONDS.convert(time, unit) + System.currentTimeMillis());
    }

    /**
     *
     *
     * @param object The object to check
     * @return false if the specified object is still in the cooldown
     */
    public boolean checkCooldown(Object object)
    {
        if (!cooldowns.containsKey(object))
            return true;

        if (System.currentTimeMillis() >= cooldowns.get(object))
        {
            cooldowns.remove(object);
            return true;
        }

        return false;
    }

    /**
     * Get the remaining time of specified object
     *
     * @param object The object to get the remaining time for
     * @return The remaining time, in the default TimeUnit
     */
    public long getRemaining(Object object)
    {
        return getRemaining(object, defaultUnit);
    }

    /**
     * Get the remaining time of specified object
     *
     * @param object The object to get the remaining time for
     * @param unit The TimeUnit for the time to be
     * @return The remaining time, in the specified TimeUnit
     */
    public long getRemaining(Object object, TimeUnit unit)
    {
        if (checkCooldown(object))
            return -1;

        return unit.convert(cooldowns.get(object) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Remove a cooldown for given object
     *
     * @param object The object to remove
     */
    public void removeCooldown(Object object)
    {
        cooldowns.remove(object);
    }
}