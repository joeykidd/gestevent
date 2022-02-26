import { useEffect } from "react";
import useApiRequest from "../shared/ApiRequestHook";
import EventList from "./components/EventList";
import NewEventForm from "./components/NewEventForm";

function Event() {
  const [callPostEventApi, event] = useApiRequest({});
  const [callGetEventsApi, events] = useApiRequest({});

  useEffect(() => {
    callGetEventsApi({ method: "GET", url: "events/" });
  }, [callGetEventsApi, event]);

  const addEvent = (data) => {
    callPostEventApi({ method: "POST", url: "events/", data });
  };
  return (
    <div className="flex-container">
      <NewEventForm onAddEvent={addEvent} />
      <EventList data={events} />
    </div>
  );
}

export default Event;
