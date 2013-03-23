package org.jpc.emulator.execution.opcodes.vm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class mul_Ed_mem extends Executable
{
    final Pointer op1;

    public mul_Ed_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
    }

    public Branch execute(Processor cpu)
    {
            cpu.af = false;
            long res64 = (0xffffffffL & op1.get32(cpu)) * (0xffffffffL & cpu.r_eax.get32());
            cpu.r_eax.set32((int)res64);
            cpu.r_edx.set32((int)(res64 >> 32));
            cpu.cf = cpu.of = (cpu.r_edx.get32() != 0);
            cpu.flagResult = (int)res64;
            cpu.flagStatus = SZP;
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}