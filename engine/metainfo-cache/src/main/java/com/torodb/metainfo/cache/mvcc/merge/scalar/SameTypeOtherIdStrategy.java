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

package com.torodb.metainfo.cache.mvcc.merge.scalar;

import com.torodb.core.transaction.metainf.ImmutableMetaDocPart;
import com.torodb.core.transaction.metainf.ImmutableMetaDocPart.Builder;
import com.torodb.core.transaction.metainf.MetaScalar;
import com.torodb.metainfo.cache.mvcc.merge.ExecutionResult;
import com.torodb.metainfo.cache.mvcc.merge.ExecutionResult.ParentDescriptionFun;


/**
 *
 */
class SameTypeOtherIdStrategy implements ScalarPartialMergeStrategy {

  @Override
  public boolean appliesTo(ScalarContext context) {
    MetaScalar changed = context.getChanged();
    MetaScalar byType = getCommitedByType(context);
    return byType != null && !byType.getIdentifier().equals(changed.getIdentifier());
  }

  @Override
  public ExecutionResult<ImmutableMetaDocPart> execute(ScalarContext context, Builder callback)
      throws IllegalArgumentException {
    return ExecutionResult.error(
        getClass(),
        parentDescFun -> getErrorMessage(parentDescFun, context)
    );
  }

  private String getErrorMessage(
      ParentDescriptionFun<ImmutableMetaDocPart> parentDescFun,
      ScalarContext context) {
    MetaScalar changed = context.getChanged();
    MetaScalar byType = getCommitedByType(context);
    assert byType != null;
    String parent = parentDescFun.apply(context.getCommitedParent());

    return "There is a previous meta scalar on " + parent + " whose type is "
        + changed.getType() + " but its identifier is " + byType.getIdentifier()
        + ". The identifier of the new one is " + changed.getIdentifier();
  }

}
