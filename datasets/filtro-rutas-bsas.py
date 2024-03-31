import geojson

# Function to filter routes within Buenos Aires province based on latitude and longitude
def filter_routes(routes, min_lat, max_lat, min_lon, max_lon):
    filtered_routes = []
    for route in routes:
        geometry = route['geometry']
        if geometry['type'] == 'MultiLineString':
            lines = geometry['coordinates']
            for line in lines:
                for lon, lat in line:
                    if min_lat <= lat <= max_lat and min_lon <= lon <= max_lon:
                        ok=True
                        for constraint in constraints:
                            if (constraint[0] <= lat and constraint[1] >= lon):
                                ok=False
                                break
                        if (-33.946343 <= lat and -59.244127 <= lon):
                            ok=False
                        if ok:
                            filtered_routes.append(route)
                        break  # Break once we find a single point within bounds to avoid duplicate entries
                else:
                    continue
                break
    return filtered_routes

# Function to read GeoJSON data from a file
def read_geojson(filename):
    with open(filename, 'r') as file:
        data = geojson.load(file)
    return data

# Function to write GeoJSON data to a file
def write_geojson(filename, data):
    with open(filename, 'w') as file:
        geojson.dump(data, file)

# Main function to filter routes within Buenos Aires province
def filter_routes_in_buenos_aires(input_filename, output_filename, min_lat, max_lat, min_lon, max_lon):
    # Read GeoJSON data
    routes_geojson = read_geojson(input_filename)
    routes = routes_geojson['features']
    
    # Filter routes within Buenos Aires province
    filtered_routes = filter_routes(routes, min_lat, max_lat, min_lon, max_lon)
    
    # Create a new GeoJSON object for filtered routes
    filtered_geojson = geojson.FeatureCollection(filtered_routes)
    
    # Write GeoJSON of filtered routes to file
    write_geojson(output_filename, filtered_geojson)
    print(f"Filtered routes saved to '{output_filename}'")

if __name__ == "__main__":
    # Replace 'argentinian_routes.geojson' with the actual path to your GeoJSON file containing all Argentinian routes
    input_filename = 'rutas_provinciales.json'
    # Replace 'buenos_aires_routes.geojson' with the desired name for the output GeoJSON file containing routes within Buenos Aires province
    output_filename = 'rutas_provinciales_bsas.json'
    
    # Define the bounding box for Buenos Aires province (replace these values with actual bounds)
    min_lat = -40.0
    max_lat = -33.255
    min_lon = -63.373470
    max_lon = -56.0

    constraint_1_lat=-34.384686
    constraint_1_lon=-61.718483
    constraint_2_lat=-34.174678
    constraint_2_lon=-61.490756
    constraint_3_lat=-33.871671
    constraint_3_lon=-61.165306
    constraint_4_lat=-33.589084
    constraint_4_lon=-60.736629
    constraint_5_lat=-33.45614
    constraint_5_lon=-60.267809
    constraint_6_lat=-33.520112
    constraint_6_lon=-60.285283
    constraints=[[constraint_1_lat,constraint_1_lon],[constraint_2_lat,constraint_2_lon],[constraint_3_lat,constraint_3_lon],[constraint_4_lat,constraint_4_lon],[constraint_5_lat,constraint_5_lon],[constraint_6_lat,constraint_6_lon]]

    
    # Filter routes within Buenos Aires province
    filter_routes_in_buenos_aires(input_filename, output_filename, min_lat, max_lat, min_lon, max_lon)

