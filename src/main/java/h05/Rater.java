package h05;

import h05.model.*;

/**
 * Rate different Components
 */
public interface Rater {

    /**
     * Consumes a given mainboard
     * @param mainboard mainboard which gets rated
     */
    void consumeMainboard(Mainboard mainboard);

    /**
     * Consume a given cpu
     * @param cpu cpu which gets rated
     */
    void consumeCpu(CPU cpu);

    /**
     * Consume a given memory
     * @param memory memory which gets rated
     */
    void consumeMemory(Memory memory);

    /**
     * Consume a given peripheral
     * @param peripheral peripheral which gets rated
     */
    void consumePeripheral(Peripheral peripheral);
}
