package com.tech4box.JRecord.occursDepending;

import com.tech4box.JRecord.Common.AbstractIndexedLine;
import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.External.Def.DependingOnDtls;

/**
 * This Interface defines a series of <i>Stratergies</i> for
 * calculating field positions when Occurs Depending isd used in Cobol.
 * 
 * @author Bruce Martin
 *
 */
public interface IOccursDependingPositionCalculation {

	public int calculateActualPosition(AbstractIndexedLine line, DependingOnDtls dependingOnDtls, int pos);

	public void checkForSizeFieldUpdate(AbstractLine line, IFieldDetail fld);

	public void clearBuffers(AbstractLine line);
}
