package com.csdy.tcondiadema.modifier.diadema;

import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.DiademaModifier;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
public class ProjectE extends DiademaModifier {
    @Override
    protected DiademaType getDiademaType() {
        return DiademaRegister.PROJECTE.get();
    }
}
