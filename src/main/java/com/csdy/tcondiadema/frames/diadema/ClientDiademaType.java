package com.csdy.tcondiadema.frames.diadema;

import java.util.function.Supplier;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


/// 客户端领域类型的抽象基类，实现后把获取实例的方法注册在注册表上<br/>
/// 不过一般情况下建议使用{@link #Create(Supplier) ClientDiademaType.Create}来创建<br/>
/// 还要用相同的ID注册一个对应的{@link DiademaType}实例，这个很重要<br/>
/// 值得一提的事，因为注册的是单个实例所以一个class也能用作多个实际的type<br/>
/// 也就是说要用时候就像Block或者MobType一样得从注册表上获取而不是new 一个
@OnlyIn(Dist.CLIENT)
public abstract class ClientDiademaType {

    /// 这个方法用以创建该类型领域相应的客户端实例，并不应被手动调用
    protected abstract ClientDiadema CreateClientInstance();


    // factory

    /// 用于创建简单的{@link ClientDiademaType}实例
    ///
    /// @param diademaClientGetter 创建客户端领域实例的方法
    public static ClientDiademaType Create(Supplier<ClientDiadema> diademaClientGetter) {
        return new CommonDiademaType(diademaClientGetter);
    }


    // impl
    private static class CommonDiademaType extends ClientDiademaType {
        CommonDiademaType(Supplier<ClientDiadema> diademaClientGetter) {
            this.diademaClientGetter = diademaClientGetter;
        }

        private final Supplier<ClientDiadema> diademaClientGetter;

        @Override protected ClientDiadema CreateClientInstance() {
            return diademaClientGetter.get();
        }
    }
}
