package ru.cwl.otus.hw15.app;


import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw15.messageSystem.Addressee;


public interface CacheService extends Addressee {


    CacheEngine getCacheEngine();

}
