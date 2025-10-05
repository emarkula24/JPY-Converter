

interface Latest {
    date: string
    baseCurrency: string
    quoteCurrency: string
    quote: number
}


interface LatestQuery {
    latest: Latest[];
}

interface GraphQLResponse {
    data: LatestQuery;
}



export const fetchExchangerate = async (api_key: string): Promise<GraphQLResponse | null> => {
    const SWOP_ENDPOINT = `https://swop.cx/graphql?api-key=${api_key}`
    const query = `
        query LatestEuro {
            latest(baseCurrency: "EUR", quoteCurrencies: ["JPY", "USD", "GBP", "AUD", "NZD","TWD", "THB", "CNY"]) {
                date
                baseCurrency
                quoteCurrency
                quote
            }
        }
    `
    try {
        const response = await fetch(SWOP_ENDPOINT, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ query })
        })

        if (!response.ok) {
            console.error("Fetch failed:", response.statusText);
            return null;
        }
        
        const json: GraphQLResponse = await response.json();
        return json
    } catch (err) {
        console.error("Error fetching exchange rates:", err);
        return null;
    }
};
