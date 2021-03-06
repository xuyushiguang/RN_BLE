package com.polidea.reactnativeble.wrapper;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.polidea.reactnativeble.utils.IdGenerator;
import com.polidea.reactnativeble.utils.IdGeneratorKey;
import com.polidea.reactnativeble.utils.UUIDConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Service {

    private interface Metadata {
        String ID = "id";
        String UUID = "uuid";
        String DEVICE_ID = "deviceID";
        String IS_PRIMARY = "isPrimary";
    }

    private Device device;
    private BluetoothGattService service;
    private int id;

    public Service(@NonNull Device device, @NonNull BluetoothGattService service) {
        this.device = device;
        this.service = service;
        this.id = IdGenerator.getIdForKey(new IdGeneratorKey(device.getNativeDevice(), service.getUuid(), service.getInstanceId()));
    }

    public int getId() {
        return this.id;
    }

    public Device getDevice() {
        return device;
    }

    public BluetoothGattService getNativeService() {
        return service;
    }

    @Nullable
    public Characteristic getCharacteristicByUUID(@NonNull UUID uuid) {
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(uuid);
        if (characteristic == null) return null;
        return new Characteristic(this, characteristic);
    }

    public List<Characteristic> getCharacteristics() {
        ArrayList<Characteristic> characteristics = new ArrayList<>(service.getCharacteristics().size());
        for (BluetoothGattCharacteristic gattCharacteristic : service.getCharacteristics()) {
            characteristics.add(new Characteristic(this, gattCharacteristic));
        }
        return characteristics;
    }

    public WritableMap toJSObject() {
        WritableMap result = Arguments.createMap();
        result.putInt(Metadata.ID, id);
        result.putString(Metadata.UUID, UUIDConverter.fromUUID(service.getUuid()));
        result.putString(Metadata.DEVICE_ID, device.getNativeDevice().getMacAddress());
        result.putBoolean(Metadata.IS_PRIMARY, service.getType() == BluetoothGattService.SERVICE_TYPE_PRIMARY);
        return result;
    }
}
