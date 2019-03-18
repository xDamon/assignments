import numpy

def minutesToTimestamp(minutes):
    """Converts a time in minutes to a HH:MM:SS timestamp.

    Args:
        minutes (float): The number of minutes to convert.

    Returns:
        A HH:MM:SS timestamp
    """
    total = minutes * 60.0

    hours, remainder = divmod(total, 3600.0)
    minutes, seconds = divmod(remainder, 60.0)

    return f"{int(hours):02d}:{int(minutes):02}:{int(seconds):02}"

def timestampToMinutes(timestamp):
    """Converts a HH:MM:SS timestamp to minutes.

    Args:
        timestamp (str): The timestamp to convert

    Returns
        A float with the number of minutes.

    """
    tokens = numpy.array(timestamp.split(":"))

    hours = float(tokens[0]) * 60.0
    minutes = float(tokens[1])
    seconds = float(tokens[2]) / 60.0

    return hours + minutes + seconds