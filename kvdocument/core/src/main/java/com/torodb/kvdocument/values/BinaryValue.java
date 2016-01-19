/*
 *     This file is part of ToroDB.
 *
 *     ToroDB is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ToroDB is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with ToroDB. If not, see <http://www.gnu.org/licenses/>.
 *
 *     Copyright (c) 2014, 8Kdata Technology
 *     
 */


package com.torodb.kvdocument.values;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.torodb.kvdocument.types.BinaryType;

/**
 *
 */
public class BinaryValue implements DocValue {

    private final ImmutableList<Byte> bytes;

    public BinaryValue(byte[] bytes) {
        ImmutableList.Builder<Byte> builder = ImmutableList.builder();

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            builder.add(b);
        }
        this.bytes = builder.build();
    }

    public BinaryValue(ImmutableList<Byte> bytes) {
        this.bytes = bytes;
    }

    /**
     * The returned value is immutable
     * <p>
     * @return
     */
    @Override
    public List<Byte> getValue() {
        return bytes;
    }

    /**
     * Returns an array that contains the same bytes that {@linkplain
     * #getValue()}.
     * <p>
     * Modifications in the given array won't affect the stored value
     * <p>
     * @return
     */
    public byte[] getArrayValue() {
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        return result;
    }

    @Override
    public BinaryType getType() {
        return BinaryType.INSTANCE;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(24);
        for (Byte aByte : bytes) {
            sb.append(String.format("%02X", aByte));
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.bytes != null ? this.bytes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BinaryValue other = (BinaryValue) obj;
        if (this.bytes != other.bytes &&
                (this.bytes == null || !this.bytes.equals(other.bytes))) {
            return false;
        }
        return true;
    }

    @Override
    public <Result, Arg> Result accept(DocValueVisitor<Result, Arg> visitor, Arg arg) {
        return visitor.visit(this, arg);
    }
}
