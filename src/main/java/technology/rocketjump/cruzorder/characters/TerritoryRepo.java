package technology.rocketjump.cruzorder.characters;


import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import technology.rocketjump.cruzorder.codegen.tables.pojos.TerritorySelection;
import technology.rocketjump.cruzorder.codegen.tables.records.TerritorySelectionRecord;

import java.util.List;
import java.util.Optional;

import static technology.rocketjump.cruzorder.codegen.tables.TerritorySelection.TERRITORY_SELECTION;

@Component
public class TerritoryRepo {

	private final DSLContext create;

	@Autowired
	public TerritoryRepo(DSLContext create) {
		this.create = create;
	}

	public boolean isAvailable(int territoryId) {
		Optional<TerritorySelection> territorySelection = create.selectFrom(TERRITORY_SELECTION)
				.where(TERRITORY_SELECTION.TERRITORY_ID.eq(territoryId))
				.fetchOptionalInto(TerritorySelection.class);

		return territorySelection.isPresent() && territorySelection.get().getDynastyId() == null;
	}

	public List<TerritorySelection> getAllTerritorySelections() {
		return create.selectFrom(TERRITORY_SELECTION)
				.orderBy(TERRITORY_SELECTION.TERRITORY_ID)
				.fetchInto(TerritorySelection.class);
	}

	public List<TerritorySelection> getAvailableTerritory(boolean isAdmin) {
		SelectConditionStep<TerritorySelectionRecord> query = create.selectFrom(TERRITORY_SELECTION)
				.where(TERRITORY_SELECTION.DYNASTY_ID.isNull());
		if (!isAdmin) {
			query = query.and(TERRITORY_SELECTION.ADMIN_ONLY.eq(false));
		}
		return query.orderBy(TERRITORY_SELECTION.TERRITORY_ID)
				.fetchInto(TerritorySelection.class);
	}

	public void assignDynasty(int dynastyId, int territoryId) {
		create.update(TERRITORY_SELECTION)
				.set(TERRITORY_SELECTION.DYNASTY_ID, dynastyId)
				.where(TERRITORY_SELECTION.TERRITORY_ID.eq(territoryId))
				.execute();
	}

	public void removeDynasty(int territoryId) {
		create.update(TERRITORY_SELECTION)
				.set(TERRITORY_SELECTION.DYNASTY_ID, (Integer)null)
				.where(TERRITORY_SELECTION.TERRITORY_ID.eq(territoryId))
				.execute();
	}

	public TerritorySelection getTerritoryForDynasty(int dynastyId) {
		return create.selectFrom(TERRITORY_SELECTION)
				.where(TERRITORY_SELECTION.DYNASTY_ID.eq(dynastyId))
				.fetchOneInto(TerritorySelection.class);
	}
}
