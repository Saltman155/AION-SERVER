package com.superywd.aion.commons.script.classlistener;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局类装卸载监听器
 * 内部维护着一个装卸载监听器链，当装卸载事件触发时，将会按照添加顺序依次调用各个监听器的事件函数
 */
public class AggregatedClassListener implements ClassListener {

    private final List<ClassListener> classListeners;

    public AggregatedClassListener(){
        classListeners = new ArrayList<>();
    }

    public AggregatedClassListener(List<ClassListener> classListeners) {
        this.classListeners = classListeners;
    }

    public List<ClassListener> getClassListeners() {
        return classListeners;
    }

    public void addClassListener(ClassListener classListener){
        this.classListeners.add(classListener);
    }

    @Override
    public void postLoad(Class<?>[] classes) {
        for(ClassListener listener : classListeners){
            listener.postLoad(classes);
        }
    }

    @Override
    public void preUnload(Class<?>[] classes) {
        for(ClassListener listener : classListeners){
            listener.preUnload(classes);
        }
    }
}
