import React from "react";
import { Form, Formik } from "formik";
import * as Yup from "yup";
import moment from "moment-timezone";
import { concatZoneIdToMoment } from "../../shared/Utils";

const MOMENT_FORMAT_WITH_ZONE_ID = "YYYY-MM-DDTHH:mm:ss.SSZ";

const initialValues = {
  name: "test99",
  description: "test",
  startDate: "",
  endDate: "",
};

const NewEventForm = ({ onAddEvent }) => {
  const zoneId = moment.tz.guess();
  const submit = async (values, { resetForm }) => {
    const payload = {
      ...values,
      startDate: concatZoneIdToMoment(
        moment(values.startDate).format(MOMENT_FORMAT_WITH_ZONE_ID),
        zoneId
      ),
      endDate: concatZoneIdToMoment(
        moment(values.endDate).format(MOMENT_FORMAT_WITH_ZONE_ID),
        zoneId
      ),
    };
    onAddEvent(payload);
    resetForm();
  };

  return (
    <Formik
      validateOnMount
      enableReinitialize
      initialValues={initialValues}
      onSubmit={submit}
      validationSchema={validationSchema}
    >
      {(formik) => (
        <Form>
          <div className="form-grid">
            <div className="form-row">
              <label htmlFor="name">Name</label>
              <input
                id="name"
                name="name"
                type="text"
                onBlur={formik.handleBlur}
                onChange={formik.handleChange}
                value={formik.values.name}
              />
            </div>
            {formik.errors.name && formik.touched.name && (
              <div className="error">{formik.errors.name}</div>
            )}
            <div className="form-row">
              <label htmlFor="name">Description</label>
              <input
                id="description"
                name="description"
                type="text"
                onBlur={formik.handleBlur}
                onChange={formik.handleChange}
                value={formik.values.description}
              />
            </div>
            <div className="form-row">
              <label htmlFor="name">Start date</label>
              <input
                id="startDate"
                name="startDate"
                type="datetime-local"
                onBlur={formik.handleBlur}
                onChange={formik.handleChange}
                value={formik.values.startDate}
              />
            </div>
            {formik.errors.startDate && formik.touched.startDate && (
              <div className="error">{formik.errors.startDate}</div>
            )}
            <div className="form-row">
              <label htmlFor="name">End date</label>
              <input
                id="endDate"
                name="endDate"
                type="datetime-local"
                onBlur={formik.handleBlur}
                onChange={formik.handleChange}
                value={formik.values.endDate}
              />
            </div>
            {formik.errors.endDate && formik.touched.endDate && (
              <div className="error">{formik.errors.endDate}</div>
            )}
            <div
              className="form-row"
              style={{
                justifyContent: "end",
              }}
            >
              <input
                disabled={
                  formik.isSubmitting || !formik.isValid || formik.isValidating
                }
                type="submit"
                value="Add event"
              />
            </div>
          </div>
        </Form>
      )}
    </Formik>
  );
};

export default NewEventForm;

const validationSchema = () =>
  Yup.object().shape({
    name: Yup.string().required().max(32),
    startDate: Yup.date().required(),
    endDate: Yup.date()
      .required()
      .min(Yup.ref("startDate"), "End date must be greater than start date"),
  });
