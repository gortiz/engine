/*
 * ToroDB
 * Copyright © 2014 8Kdata Technology (www.8kdata.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.torodb.metainfo.cache.mvcc.merge.field;

import com.torodb.core.transaction.metainf.ImmutableMetaCollection;
import com.torodb.core.transaction.metainf.ImmutableMetaDocPart;
import com.torodb.core.transaction.metainf.MetaElementState;
import com.torodb.core.transaction.metainf.MetaField;
import com.torodb.core.transaction.metainf.MutableMetaCollection;
import com.torodb.core.transaction.metainf.MutableMetaDocPart;
import com.torodb.metainfo.cache.mvcc.merge.DefaultMergeContext;

/**
 *
 */
public class FieldContext 
    extends DefaultMergeContext<ImmutableMetaDocPart, MetaField, MutableMetaDocPart> {

  private final ImmutableMetaCollection commitedCollection;
  private final MutableMetaCollection uncommitedCollection;

  public FieldContext(
      ImmutableMetaDocPart commitedParent,
      MetaField changed,
      MutableMetaDocPart uncommitedParent,
      ImmutableMetaCollection commitedCollection,
      MutableMetaCollection uncommitedCollection) {
    super(commitedParent, changed, MetaElementState.ADDED, uncommitedParent);
    this.commitedCollection = commitedCollection;
    this.uncommitedCollection = uncommitedCollection;
  }

  public ImmutableMetaCollection getCommitedCollection() {
    return commitedCollection;
  }

  public MutableMetaCollection getUncommitedCollection() {
    return uncommitedCollection;
  }

}
