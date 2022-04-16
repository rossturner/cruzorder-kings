const byId = {};

const TerritoryStore = {

    initialised: false,

    add: (territoryList) => {
        if (!TerritoryStore.initialised) {
            territoryList.forEach(t => {
                byId[t.territoryId] = t;
            });
            TerritoryStore.initialised = true;
        }
    },

    getById: (territoryId) => {
        return byId[territoryId];
    }
};

export default TerritoryStore;