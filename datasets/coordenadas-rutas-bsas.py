import json


def preprocess_coordinates(coordinates):
    processed_coordinates = []
    for coord_pair in coordinates:
        for i in range(0, len(coord_pair), 2):
            print(i)
            processed_coordinates.append(
                {"lat": coord_pair[i + 1], "long": coord_pair[i]}
            )
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
    output_filename = "puntos-con-flatten.json"
    process_file(input_filename, output_filename)
