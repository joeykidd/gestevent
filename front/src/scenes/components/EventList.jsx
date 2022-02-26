import React from "react";
import moment from "moment";

const DATE_FORMAT = "YYYY-MM-DD HH:mm";

const EventList = ({ data }) => {
  return (
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Description</th>
          <th>Start Date in local timezone</th>
          <th>Start Date in UTC</th>
          <th>End Date in local timezone</th>
          <th>End Date in UTC</th>
        </tr>
      </thead>
      <tbody>
        {data &&
          data.content &&
          data.content.map((event) => (
            <tr key={event.name}>
              <td>{event.name}</td>
              <td>{event.description}</td>
              <td>{moment(event.startDate).local().format(DATE_FORMAT)}</td>
              <td>{moment(event.startDate).utc().format(DATE_FORMAT)}</td>
              <td>{moment(event.endDate).local().format(DATE_FORMAT)}</td>
              <td>{moment(event.endDate).utc().format(DATE_FORMAT)}</td>
            </tr>
          ))}
      </tbody>
    </table>
  );
};

export default EventList;
