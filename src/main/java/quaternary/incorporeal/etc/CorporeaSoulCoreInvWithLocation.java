package quaternary.incorporeal.etc;

import vazkii.botania.api.corporea.InvWithLocation;

import java.util.UUID;

public class CorporeaSoulCoreInvWithLocation extends InvWithLocation {
	public CorporeaSoulCoreInvWithLocation(InvWithLocation delegate, UUID ownerUUID) {
		super(delegate.handler, delegate.world, delegate.pos);
		this.delegate = delegate;
		this.ownerUUID = ownerUUID;
	}
	
	public final InvWithLocation delegate;
	public final UUID ownerUUID;
	
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof CorporeaSoulCoreInvWithLocation) {
			return delegate.equals(o) && ((CorporeaSoulCoreInvWithLocation) o).ownerUUID.equals(ownerUUID);
		} else return false;
	}
}
