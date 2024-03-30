import json
import requests

def preprocess_coordinates(coordinates):
    processed_coordinates = []
    for coord_pair in coordinates:
        print(f"Pair {coordinates.index(coord_pair)} of {len(coordinates)}")
        try:
            for i in range(0, len(coord_pair), 2):
                processed_coordinates.append(
                    {
                        "lat": coord_pair[i + 1], 
                        "long": coord_pair[i], 
                        "dpto": requests.get(f"https://apis.datos.gob.ar/georef/api/ubicacion?lat={coord_pair[i + 1]}&lon={coord_pair[i]}").json()['ubicacion']['departamento']['nombre']
                    }
                )
        except:
            break
    # return json
    return json.dumps(processed_coordinates)


def process_file(input_filename, output_filename):
    with open(input_filename, "r") as f:
        data = json.load(f)
    preprocessed = preprocess_coordinates(data)
    with open(output_filename, "w") as f:
        f.write(preprocessed)


if __name__ == "__main__":
    input_filename = "puntos-sin-flatten.json"
    output_filename = "puntos-con-flatten-dptos.json"
    process_file(input_filename, output_filename)
