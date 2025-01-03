import type { xml } from "d3"

const POSITION_FACTOR = 1000

export const PositionMapper = {
    toX: (longitude: number) => longitude * POSITION_FACTOR,
    toY: (latitude: number) => latitude * POSITION_FACTOR,
    toLongitude: (x: number) => x / POSITION_FACTOR,
    toLatitude: (y: number) => y / POSITION_FACTOR,
    toGeoPosition: (x: number, y: number) => ({
        longitude: PositionMapper.toLongitude(x),
        latitude: PositionMapper.toLatitude(y)
    }),
    toXYPosition: (longitude: number, latitude: number) => ({
        x: PositionMapper.toX(longitude),
        y: PositionMapper.toY(latitude)
    })
}
